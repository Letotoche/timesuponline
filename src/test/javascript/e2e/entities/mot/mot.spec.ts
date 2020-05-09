import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { MotComponentsPage, MotDeleteDialog, MotUpdatePage } from './mot.page-object';

const expect = chai.expect;

describe('Mot e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let motComponentsPage: MotComponentsPage;
  let motUpdatePage: MotUpdatePage;
  let motDeleteDialog: MotDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Mots', async () => {
    await navBarPage.goToEntity('mot');
    motComponentsPage = new MotComponentsPage();
    await browser.wait(ec.visibilityOf(motComponentsPage.title), 5000);
    expect(await motComponentsPage.getTitle()).to.eq('timesuponlineApp.mot.home.title');
    await browser.wait(ec.or(ec.visibilityOf(motComponentsPage.entities), ec.visibilityOf(motComponentsPage.noResult)), 1000);
  });

  it('should load create Mot page', async () => {
    await motComponentsPage.clickOnCreateButton();
    motUpdatePage = new MotUpdatePage();
    expect(await motUpdatePage.getPageTitle()).to.eq('timesuponlineApp.mot.home.createOrEditLabel');
    await motUpdatePage.cancel();
  });

  it('should create and save Mots', async () => {
    const nbButtonsBeforeCreate = await motComponentsPage.countDeleteButtons();

    await motComponentsPage.clickOnCreateButton();

    await promise.all([
      motUpdatePage.setMotInput('mot'),
      motUpdatePage.etatSelectLastOption(),
      motUpdatePage.auteurSelectLastOption(),
      motUpdatePage.partieSelectLastOption(),
      motUpdatePage.tourDeJeuSelectLastOption()
    ]);

    expect(await motUpdatePage.getMotInput()).to.eq('mot', 'Expected Mot value to be equals to mot');

    await motUpdatePage.save();
    expect(await motUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await motComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Mot', async () => {
    const nbButtonsBeforeDelete = await motComponentsPage.countDeleteButtons();
    await motComponentsPage.clickOnLastDeleteButton();

    motDeleteDialog = new MotDeleteDialog();
    expect(await motDeleteDialog.getDialogTitle()).to.eq('timesuponlineApp.mot.delete.question');
    await motDeleteDialog.clickOnConfirmButton();

    expect(await motComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
