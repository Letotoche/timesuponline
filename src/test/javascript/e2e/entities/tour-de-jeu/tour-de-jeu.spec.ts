import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TourDeJeuComponentsPage, TourDeJeuDeleteDialog, TourDeJeuUpdatePage } from './tour-de-jeu.page-object';

const expect = chai.expect;

describe('TourDeJeu e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let tourDeJeuComponentsPage: TourDeJeuComponentsPage;
  let tourDeJeuUpdatePage: TourDeJeuUpdatePage;
  let tourDeJeuDeleteDialog: TourDeJeuDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TourDeJeus', async () => {
    await navBarPage.goToEntity('tour-de-jeu');
    tourDeJeuComponentsPage = new TourDeJeuComponentsPage();
    await browser.wait(ec.visibilityOf(tourDeJeuComponentsPage.title), 5000);
    expect(await tourDeJeuComponentsPage.getTitle()).to.eq('timesuponlineApp.tourDeJeu.home.title');
    await browser.wait(ec.or(ec.visibilityOf(tourDeJeuComponentsPage.entities), ec.visibilityOf(tourDeJeuComponentsPage.noResult)), 1000);
  });

  it('should load create TourDeJeu page', async () => {
    await tourDeJeuComponentsPage.clickOnCreateButton();
    tourDeJeuUpdatePage = new TourDeJeuUpdatePage();
    expect(await tourDeJeuUpdatePage.getPageTitle()).to.eq('timesuponlineApp.tourDeJeu.home.createOrEditLabel');
    await tourDeJeuUpdatePage.cancel();
  });

  it('should create and save TourDeJeus', async () => {
    const nbButtonsBeforeCreate = await tourDeJeuComponentsPage.countDeleteButtons();

    await tourDeJeuComponentsPage.clickOnCreateButton();

    await promise.all([
      tourDeJeuUpdatePage.setTempsRestantInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tourDeJeuUpdatePage.setDateDebuteInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      tourDeJeuUpdatePage.userSelectLastOption(),
      tourDeJeuUpdatePage.partieSelectLastOption()
    ]);

    expect(await tourDeJeuUpdatePage.getTempsRestantInput()).to.contain(
      '2001-01-01T02:30',
      'Expected tempsRestant value to be equals to 2000-12-31'
    );
    expect(await tourDeJeuUpdatePage.getDateDebuteInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateDebute value to be equals to 2000-12-31'
    );

    await tourDeJeuUpdatePage.save();
    expect(await tourDeJeuUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await tourDeJeuComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TourDeJeu', async () => {
    const nbButtonsBeforeDelete = await tourDeJeuComponentsPage.countDeleteButtons();
    await tourDeJeuComponentsPage.clickOnLastDeleteButton();

    tourDeJeuDeleteDialog = new TourDeJeuDeleteDialog();
    expect(await tourDeJeuDeleteDialog.getDialogTitle()).to.eq('timesuponlineApp.tourDeJeu.delete.question');
    await tourDeJeuDeleteDialog.clickOnConfirmButton();

    expect(await tourDeJeuComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
