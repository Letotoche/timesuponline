import { element, by, ElementFinder } from 'protractor';

export class TourDeJeuComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-tour-de-jeu div table .btn-danger'));
  title = element.all(by.css('jhi-tour-de-jeu div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class TourDeJeuUpdatePage {
  pageTitle = element(by.id('jhi-tour-de-jeu-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  tempsRestantInput = element(by.id('field_tempsRestant'));
  dateDebuteInput = element(by.id('field_dateDebute'));

  userSelect = element(by.id('field_user'));
  partieSelect = element(by.id('field_partie'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTempsRestantInput(tempsRestant: string): Promise<void> {
    await this.tempsRestantInput.sendKeys(tempsRestant);
  }

  async getTempsRestantInput(): Promise<string> {
    return await this.tempsRestantInput.getAttribute('value');
  }

  async setDateDebuteInput(dateDebute: string): Promise<void> {
    await this.dateDebuteInput.sendKeys(dateDebute);
  }

  async getDateDebuteInput(): Promise<string> {
    return await this.dateDebuteInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async partieSelectLastOption(): Promise<void> {
    await this.partieSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async partieSelectOption(option: string): Promise<void> {
    await this.partieSelect.sendKeys(option);
  }

  getPartieSelect(): ElementFinder {
    return this.partieSelect;
  }

  async getPartieSelectedOption(): Promise<string> {
    return await this.partieSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class TourDeJeuDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-tourDeJeu-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-tourDeJeu'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
