import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEquipe, Equipe } from 'app/shared/model/equipe.model';
import { EquipeService } from './equipe.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPartie } from 'app/shared/model/partie.model';
import { PartieService } from 'app/entities/partie/partie.service';

type SelectableEntity = IUser | IPartie;

@Component({
  selector: 'jhi-equipe-update',
  templateUrl: './equipe-update.component.html'
})
export class EquipeUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  parties: IPartie[] = [];

  editForm = this.fb.group({
    id: [],
    nom: [null, [Validators.required]],
    score1: [],
    score2: [],
    score3: [],
    membres: [],
    partie: []
  });

  constructor(
    protected equipeService: EquipeService,
    protected userService: UserService,
    protected partieService: PartieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ equipe }) => {
      this.updateForm(equipe);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.partieService.query().subscribe((res: HttpResponse<IPartie[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(equipe: IEquipe): void {
    this.editForm.patchValue({
      id: equipe.id,
      nom: equipe.nom,
      score1: equipe.score1,
      score2: equipe.score2,
      score3: equipe.score3,
      membres: equipe.membres,
      partie: equipe.partie
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const equipe = this.createFromForm();
    if (equipe.id !== undefined) {
      this.subscribeToSaveResponse(this.equipeService.update(equipe));
    } else {
      this.subscribeToSaveResponse(this.equipeService.create(equipe));
    }
  }

  private createFromForm(): IEquipe {
    return {
      ...new Equipe(),
      id: this.editForm.get(['id'])!.value,
      nom: this.editForm.get(['nom'])!.value,
      score1: this.editForm.get(['score1'])!.value,
      score2: this.editForm.get(['score2'])!.value,
      score3: this.editForm.get(['score3'])!.value,
      membres: this.editForm.get(['membres'])!.value,
      partie: this.editForm.get(['partie'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEquipe>>): void {
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

  getSelected(selectedVals: IUser[], option: IUser): IUser {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
