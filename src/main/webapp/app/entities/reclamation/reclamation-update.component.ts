import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IReclamation, Reclamation } from 'app/shared/model/reclamation.model';
import { ReclamationService } from './reclamation.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';
import { IMot } from 'app/shared/model/mot.model';
import { MotService } from 'app/entities/mot/mot.service';

type SelectableEntity = IUser | IMot;

@Component({
  selector: 'jhi-reclamation-update',
  templateUrl: './reclamation-update.component.html'
})
export class ReclamationUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  mots: IMot[] = [];

  editForm = this.fb.group({
    id: [],
    etat: [null, [Validators.required]],
    auteur: [],
    mot: []
  });

  constructor(
    protected reclamationService: ReclamationService,
    protected userService: UserService,
    protected motService: MotService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ reclamation }) => {
      this.updateForm(reclamation);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));

      this.motService
        .query({ filter: 'reclamation-is-null' })
        .pipe(
          map((res: HttpResponse<IMot[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IMot[]) => {
          if (!reclamation.mot || !reclamation.mot.id) {
            this.mots = resBody;
          } else {
            this.motService
              .find(reclamation.mot.id)
              .pipe(
                map((subRes: HttpResponse<IMot>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IMot[]) => (this.mots = concatRes));
          }
        });
    });
  }

  updateForm(reclamation: IReclamation): void {
    this.editForm.patchValue({
      id: reclamation.id,
      etat: reclamation.etat,
      auteur: reclamation.auteur,
      mot: reclamation.mot
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const reclamation = this.createFromForm();
    if (reclamation.id !== undefined) {
      this.subscribeToSaveResponse(this.reclamationService.update(reclamation));
    } else {
      this.subscribeToSaveResponse(this.reclamationService.create(reclamation));
    }
  }

  private createFromForm(): IReclamation {
    return {
      ...new Reclamation(),
      id: this.editForm.get(['id'])!.value,
      etat: this.editForm.get(['etat'])!.value,
      auteur: this.editForm.get(['auteur'])!.value,
      mot: this.editForm.get(['mot'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReclamation>>): void {
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
