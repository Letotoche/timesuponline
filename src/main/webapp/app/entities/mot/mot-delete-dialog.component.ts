import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMot } from 'app/shared/model/mot.model';
import { MotService } from './mot.service';

@Component({
  templateUrl: './mot-delete-dialog.component.html'
})
export class MotDeleteDialogComponent {
  mot?: IMot;

  constructor(protected motService: MotService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.motService.delete(id).subscribe(() => {
      this.eventManager.broadcast('motListModification');
      this.activeModal.close();
    });
  }
}
