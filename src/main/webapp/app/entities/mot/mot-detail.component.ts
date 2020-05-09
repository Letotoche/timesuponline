import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMot } from 'app/shared/model/mot.model';

@Component({
  selector: 'jhi-mot-detail',
  templateUrl: './mot-detail.component.html'
})
export class MotDetailComponent implements OnInit {
  mot: IMot | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mot }) => (this.mot = mot));
  }

  previousState(): void {
    window.history.back();
  }
}
