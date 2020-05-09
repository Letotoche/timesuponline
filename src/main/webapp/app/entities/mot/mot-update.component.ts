import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IMot, Mot } from 'app/shared/model/mot.model';
import { MotService } from './mot.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IPartie } from 'app/shared/model/partie.model';
import { PartieService } from 'app/entities/partie/partie.service';

type SelectableEntity = IUser | IPartie;

@Component({
  selector: 'jhi-mot-update',
  templateUrl: './mot-update.component.html'
})
export class MotUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  parties: IPartie[] = [];

  editForm = this.fb.group({
    id: [],
    mot: [null, [Validators.required]],
    etat: [null, [Validators.required]],
    auteur: [],
    partie: []
  });

  constructor(
    protected motService: MotService,
    protected userService: UserService,
    protected partieService: PartieService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mot }) => {
      this.updateForm(mot);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.partieService.query().subscribe((res: HttpResponse<IPartie[]>) => (this.parties = res.body || []));
    });
  }

  updateForm(mot: IMot): void {
    this.editForm.patchValue({
      id: mot.id,
      mot: mot.mot,
      etat: mot.etat,
      auteur: mot.auteur,
      partie: mot.partie
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const mot = this.createFromForm();
    if (mot.id !== undefined) {
      this.subscribeToSaveResponse(this.motService.update(mot));
    } else {
      this.subscribeToSaveResponse(this.motService.create(mot));
    }
  }

  private createFromForm(): IMot {
    return {
      ...new Mot(),
      id: this.editForm.get(['id'])!.value,
      mot: this.editForm.get(['mot'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      auteur: this.editForm.get(['auteur'])!.value,
      partie: this.editForm.get(['partie'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMot>>): void {
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
