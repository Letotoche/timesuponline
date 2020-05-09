import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMot } from 'app/shared/model/mot.model';
import { MotService } from './mot.service';
import { MotDeleteDialogComponent } from './mot-delete-dialog.component';

@Component({
  selector: 'jhi-mot',
  templateUrl: './mot.component.html'
})
export class MotComponent implements OnInit, OnDestroy {
  mots?: IMot[];
  eventSubscriber?: Subscription;

  constructor(protected motService: MotService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.motService.query().subscribe((res: HttpResponse<IMot[]>) => (this.mots = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInMots();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IMot): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInMots(): void {
    this.eventSubscriber = this.eventManager.subscribe('motListModification', () => this.loadAll());
  }

  delete(mot: IMot): void {
    const modalRef = this.modalService.open(MotDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mot = mot;
  }
}
