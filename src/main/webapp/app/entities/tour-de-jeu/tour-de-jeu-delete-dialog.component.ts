import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITourDeJeu } from 'app/shared/model/tour-de-jeu.model';
import { TourDeJeuService } from './tour-de-jeu.service';

@Component({
  templateUrl: './tour-de-jeu-delete-dialog.component.html'
})
export class TourDeJeuDeleteDialogComponent {
  tourDeJeu?: ITourDeJeu;

  constructor(protected tourDeJeuService: TourDeJeuService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tourDeJeuService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tourDeJeuListModification');
      this.activeModal.close();
    });
  }
}
