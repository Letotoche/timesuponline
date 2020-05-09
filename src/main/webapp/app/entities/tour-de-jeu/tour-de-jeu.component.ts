import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITourDeJeu } from 'app/shared/model/tour-de-jeu.model';
import { TourDeJeuService } from './tour-de-jeu.service';
import { TourDeJeuDeleteDialogComponent } from './tour-de-jeu-delete-dialog.component';

@Component({
  selector: 'jhi-tour-de-jeu',
  templateUrl: './tour-de-jeu.component.html'
})
export class TourDeJeuComponent implements OnInit, OnDestroy {
  tourDeJeus?: ITourDeJeu[];
  eventSubscriber?: Subscription;

  constructor(protected tourDeJeuService: TourDeJeuService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.tourDeJeuService.query().subscribe((res: HttpResponse<ITourDeJeu[]>) => (this.tourDeJeus = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTourDeJeus();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITourDeJeu): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTourDeJeus(): void {
    this.eventSubscriber = this.eventManager.subscribe('tourDeJeuListModification', () => this.loadAll());
  }

  delete(tourDeJeu: ITourDeJeu): void {
    const modalRef = this.modalService.open(TourDeJeuDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tourDeJeu = tourDeJeu;
  }
}
