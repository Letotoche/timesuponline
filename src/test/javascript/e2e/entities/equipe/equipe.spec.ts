import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EquipeComponentsPage, EquipeDeleteDialog, EquipeUpdatePage } from './equipe.page-object';

const expect = chai.expect;

describe('Equipe e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let equipeComponentsPage: EquipeComponentsPage;
  let equipeUpdatePage: EquipeUpdatePage;
  let equipeDeleteDialog: EquipeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Equipes', async () => {
    await navBarPage.goToEntity('equipe');
    equipeComponentsPage = new EquipeComponentsPage();
    await browser.wait(ec.visibilityOf(equipeComponentsPage.title), 5000);
    expect(await equipeComponentsPage.getTitle()).to.eq('timesuponlineApp.equipe.home.title');
    await browser.wait(ec.or(ec.visibilityOf(equipeComponentsPage.entities), ec.visibilityOf(equipeComponentsPage.noResult)), 1000);
  });

  it('should load create Equipe page', async () => {
    await equipeComponentsPage.clickOnCreateButton();
    equipeUpdatePage = new EquipeUpdatePage();
    expect(await equipeUpdatePage.getPageTitle()).to.eq('timesuponlineApp.equipe.home.createOrEditLabel');
    await equipeUpdatePage.cancel();
  });

  it('should create and save Equipes', async () => {
    const nbButtonsBeforeCreate = await equipeComponentsPage.countDeleteButtons();

    await equipeComponentsPage.clickOnCreateButton();

    await promise.all([
      equipeUpdatePage.setNomInput('nom'),
      equipeUpdatePage.setScore1Input('5'),
      equipeUpdatePage.setScore2Input('5'),
      equipeUpdatePage.setScore3Input('5'),
      // equipeUpdatePage.membreSelectLastOption(),
      equipeUpdatePage.partieSelectLastOption()
    ]);

    expect(await equipeUpdatePage.getNomInput()).to.eq('nom', 'Expected Nom value to be equals to nom');
    expect(await equipeUpdatePage.getScore1Input()).to.eq('5', 'Expected score1 value to be equals to 5');
    expect(await equipeUpdatePage.getScore2Input()).to.eq('5', 'Expected score2 value to be equals to 5');
    expect(await equipeUpdatePage.getScore3Input()).to.eq('5', 'Expected score3 value to be equals to 5');

    await equipeUpdatePage.save();
    expect(await equipeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await equipeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Equipe', async () => {
    const nbButtonsBeforeDelete = await equipeComponentsPage.countDeleteButtons();
    await equipeComponentsPage.clickOnLastDeleteButton();

    equipeDeleteDialog = new EquipeDeleteDialog();
    expect(await equipeDeleteDialog.getDialogTitle()).to.eq('timesuponlineApp.equipe.delete.question');
    await equipeDeleteDialog.clickOnConfirmButton();

    expect(await equipeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
