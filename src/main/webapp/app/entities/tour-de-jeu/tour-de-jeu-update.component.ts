import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITourDeJeu, TourDeJeu } from 'app/shared/model/tour-de-jeu.model';
import { TourDeJeuService } from './tour-de-jeu.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPartie } from 'app/shared/model/partie.model';
import { PartieService } from 'app/entities/partie/partie.service';

type SelectableEntity = IUser | IPartie;

@Component({
  selector: 'jhi-tour-de-jeu-update',
  templateUrl: './tour-de-jeu-update.component.html'
})
export class TourDeJeuUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  parties: IPartie[] = [];

  editForm = this.fb.group({
    id: [],
    tempsRestant: [],
    dateDebute: [],
    user: [],
    partie: []
  });

  constructor(
    protected tourDeJeuService: TourDeJeuService,
    protected userService: UserService,
    protected partieService: PartieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tourDeJeu }) => {
      if (!tourDeJeu.id) {
        const today = moment().startOf('day');
        tourDeJeu.tempsRestant = today;
        tourDeJeu.dateDebute = today;
      }

      this.updateForm(tourDeJeu);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.partieService.query().subscribe((res: HttpResponse<IPartie[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(tourDeJeu: ITourDeJeu): void {
    this.editForm.patchValue({
      id: tourDeJeu.id,
      tempsRestant: tourDeJeu.tempsRestant ? tourDeJeu.tempsRestant.format(DATE_TIME_FORMAT) : null,
      dateDebute: tourDeJeu.dateDebute ? tourDeJeu.dateDebute.format(DATE_TIME_FORMAT) : null,
      user: tourDeJeu.user,
      partie: tourDeJeu.partie
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tourDeJeu = this.createFromForm();
    if (tourDeJeu.id !== undefined) {
      this.subscribeToSaveResponse(this.tourDeJeuService.update(tourDeJeu));
    } else {
      this.subscribeToSaveResponse(this.tourDeJeuService.create(tourDeJeu));
    }
  }

  private createFromForm(): ITourDeJeu {
    return {
      ...new TourDeJeu(),
      id: this.editForm.get(['id'])!.value,
      tempsRestant: this.editForm.get(['tempsRestant'])!.value
        ? moment(this.editForm.get(['tempsRestant'])!.value, DATE_TIME_FORMAT)
        : undefined,
      dateDebute: this.editForm.get(['dateDebute'])!.value ? moment(this.editForm.get(['dateDebute'])!.value, DATE_TIME_FORMAT) : undefined,
      user: this.editForm.get(['user'])!.value,
      partie: this.editForm.get(['partie'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITourDeJeu>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
