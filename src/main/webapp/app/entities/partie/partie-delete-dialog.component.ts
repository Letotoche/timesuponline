import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPartie } from 'app/shared/model/partie.model';
import { PartieService } from './partie.service';

@Component({
  templateUrl: './partie-delete-dialog.component.html'
})
export class PartieDeleteDialogComponent {
  partie?: IPartie;

  constructor(protected partieService: PartieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.partieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('partieListModification');
      this.activeModal.close();
    });
  }
}
