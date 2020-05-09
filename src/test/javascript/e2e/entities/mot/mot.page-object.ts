import { element, by, ElementFinder } from 'protractor';

export class MotComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-mot div table .btn-danger'));
  title = element.all(by.css('jhi-mot div h2#page-heading span')).first();
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

export class MotUpdatePage {
  pageTitle = element(by.id('jhi-mot-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  motInput = element(by.id('field_mot'));
  etatSelect = element(by.id('field_etat'));

  auteurSelect = element(by.id('field_auteur'));
  partieSelect = element(by.id('field_partie'));
  tourDeJeuSelect = element(by.id('field_tourDeJeu'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMotInput(mot: string): Promise<void> {
    await this.motInput.sendKeys(mot);
  }

  async getMotInput(): Promise<string> {
    return await this.motInput.getAttribute('value');
  }

  async setEtatSelect(etat: string): Promise<void> {
    await this.etatSelect.sendKeys(etat);
  }

  async getEtatSelect(): Promise<string> {
    return await this.etatSelect.element(by.css('option:checked')).getText();
  }

  async etatSelectLastOption(): Promise<void> {
    await this.etatSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async auteurSelectLastOption(): Promise<void> {
    await this.auteurSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async auteurSelectOption(option: string): Promise<void> {
    await this.auteurSelect.sendKeys(option);
  }

  getAuteurSelect(): ElementFinder {
    return this.auteurSelect;
  }

  async getAuteurSelectedOption(): Promise<string> {
    return await this.auteurSelect.element(by.css('option:checked')).getText();
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

  async tourDeJeuSelectLastOption(): Promise<void> {
    await this.tourDeJeuSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async tourDeJeuSelectOption(option: string): Promise<void> {
    await this.tourDeJeuSelect.sendKeys(option);
  }

  getTourDeJeuSelect(): ElementFinder {
    return this.tourDeJeuSelect;
  }

  async getTourDeJeuSelectedOption(): Promise<string> {
    return await this.tourDeJeuSelect.element(by.css('option:checked')).getText();
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

export class MotDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-mot-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-mot'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
