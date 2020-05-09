import { element, by, ElementFinder } from 'protractor';

export class ReclamationComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-reclamation div table .btn-danger'));
  title = element.all(by.css('jhi-reclamation div h2#page-heading span')).first();
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

export class ReclamationUpdatePage {
  pageTitle = element(by.id('jhi-reclamation-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  etatSelect = element(by.id('field_etat'));

  auteurSelect = element(by.id('field_auteur'));
  motSelect = element(by.id('field_mot'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async motSelectLastOption(): Promise<void> {
    await this.motSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async motSelectOption(option: string): Promise<void> {
    await this.motSelect.sendKeys(option);
  }

  getMotSelect(): ElementFinder {
    return this.motSelect;
  }

  async getMotSelectedOption(): Promise<string> {
    return await this.motSelect.element(by.css('option:checked')).getText();
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

export class ReclamationDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-reclamation-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-reclamation'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
