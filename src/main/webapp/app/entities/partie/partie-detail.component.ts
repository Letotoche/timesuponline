import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPartie } from 'app/shared/model/partie.model';

@Component({
  selector: 'jhi-partie-detail',
  templateUrl: './partie-detail.component.html'
})
export class PartieDetailComponent implements OnInit {
  partie: IPartie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partie }) => (this.partie = partie));
  }

  previousState(): void {
    window.history.back();
  }
}
