import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartieComponentsPage, PartieDeleteDialog, PartieUpdatePage } from './partie.page-object';

const expect = chai.expect;

describe('Partie e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let partieComponentsPage: PartieComponentsPage;
  let partieUpdatePage: PartieUpdatePage;
  let partieDeleteDialog: PartieDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Parties', async () => {
    await navBarPage.goToEntity('partie');
    partieComponentsPage = new PartieComponentsPage();
    await browser.wait(ec.visibilityOf(partieComponentsPage.title), 5000);
    expect(await partieComponentsPage.getTitle()).to.eq('timesuponlineApp.partie.home.title');
    await browser.wait(ec.or(ec.visibilityOf(partieComponentsPage.entities), ec.visibilityOf(partieComponentsPage.noResult)), 1000);
  });

  it('should load create Partie page', async () => {
    await partieComponentsPage.clickOnCreateButton();
    partieUpdatePage = new PartieUpdatePage();
    expect(await partieUpdatePage.getPageTitle()).to.eq('timesuponlineApp.partie.home.createOrEditLabel');
    await partieUpdatePage.cancel();
  });

  it('should create and save Parties', async () => {
    const nbButtonsBeforeCreate = await partieComponentsPage.countDeleteButtons();

    await partieComponentsPage.clickOnCreateButton();

    await promise.all([
      partieUpdatePage.setIntituleInput('intitule')
      // partieUpdatePage.joueurSelectLastOption(),
    ]);

    expect(await partieUpdatePage.getIntituleInput()).to.eq('intitule', 'Expected Intitule value to be equals to intitule');

    await partieUpdatePage.save();
    expect(await partieUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await partieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Partie', async () => {
    const nbButtonsBeforeDelete = await partieComponentsPage.countDeleteButtons();
    await partieComponentsPage.clickOnLastDeleteButton();

    partieDeleteDialog = new PartieDeleteDialog();
    expect(await partieDeleteDialog.getDialogTitle()).to.eq('timesuponlineApp.partie.delete.question');
    await partieDeleteDialog.clickOnConfirmButton();

    expect(await partieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
