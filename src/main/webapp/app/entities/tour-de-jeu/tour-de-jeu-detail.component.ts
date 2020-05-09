import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITourDeJeu } from 'app/shared/model/tour-de-jeu.model';

@Component({
  selector: 'jhi-tour-de-jeu-detail',
  templateUrl: './tour-de-jeu-detail.component.html'
})
export class TourDeJeuDetailComponent implements OnInit {
  tourDeJeu: ITourDeJeu | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tourDeJeu }) => (this.tourDeJeu = tourDeJeu));
  }

  previousState(): void {
    window.history.back();
  }
}
