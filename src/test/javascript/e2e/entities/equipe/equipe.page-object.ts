import { element, by, ElementFinder } from 'protractor';

export class EquipeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-equipe div table .btn-danger'));
  title = element.all(by.css('jhi-equipe div h2#page-heading span')).first();
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

export class EquipeUpdatePage {
  pageTitle = element(by.id('jhi-equipe-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nomInput = element(by.id('field_nom'));
  score1Input = element(by.id('field_score1'));
  score2Input = element(by.id('field_score2'));
  score3Input = element(by.id('field_score3'));

  membreSelect = element(by.id('field_membre'));
  partieSelect = element(by.id('field_partie'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNomInput(nom: string): Promise<void> {
    await this.nomInput.sendKeys(nom);
  }

  async getNomInput(): Promise<string> {
    return await this.nomInput.getAttribute('value');
  }

  async setScore1Input(score1: string): Promise<void> {
    await this.score1Input.sendKeys(score1);
  }

  async getScore1Input(): Promise<string> {
    return await this.score1Input.getAttribute('value');
  }

  async setScore2Input(score2: string): Promise<void> {
    await this.score2Input.sendKeys(score2);
  }

  async getScore2Input(): Promise<string> {
    return await this.score2Input.getAttribute('value');
  }

  async setScore3Input(score3: string): Promise<void> {
    await this.score3Input.sendKeys(score3);
  }

  async getScore3Input(): Promise<string> {
    return await this.score3Input.getAttribute('value');
  }

  async membreSelectLastOption(): Promise<void> {
    await this.membreSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async membreSelectOption(option: string): Promise<void> {
    await this.membreSelect.sendKeys(option);
  }

  getMembreSelect(): ElementFinder {
    return this.membreSelect;
  }

  async getMembreSelectedOption(): Promise<string> {
    return await this.membreSelect.element(by.css('option:checked')).getText();
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

export class EquipeDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-equipe-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-equipe'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
