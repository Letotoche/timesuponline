import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPartie, Partie } from 'app/shared/model/partie.model';
import { PartieService } from './partie.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-partie-update',
  templateUrl: './partie-update.component.html'
})
export class PartieUpdateComponent implements OnInit {
  isSaving = false;
  users: IUser[] = [];
  dateCreationDp: any;

  editForm = this.fb.group({
    id: [],
    intitule: [null, [Validators.required, Validators.maxLength(100)]],
    dateCreation: [],
    phase: [null, [Validators.required]],
    nbMots: [],
    tempsSablier: [],
    master: [],
    joueurs: []
  });

  constructor(
    protected partieService: PartieService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ partie }) => {
      this.updateForm(partie);

      this.userService.query().subscribe((res: HttpResponse<IUser[]>) => (this.users = res.body || []));
    });
  }

  updateForm(partie: IPartie): void {
    this.editForm.patchValue({
      id: partie.id,
      intitule: partie.intitule,
      dateCreation: partie.dateCreation,
      phase: partie.phase,
      nbMots: partie.nbMots,
      tempsSablier: partie.tempsSablier,
      master: partie.master,
      joueurs: partie.joueurs
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const partie = this.createFromForm();
    if (partie.id !== undefined) {
      this.subscribeToSaveResponse(this.partieService.update(partie));
    } else {
      this.subscribeToSaveResponse(this.partieService.create(partie));
    }
  }

  private createFromForm(): IPartie {
    return {
      ...new Partie(),
      id: this.editForm.get(['id'])!.value,
      intitule: this.editForm.get(['intitule'])!.value,
      dateCreation: this.editForm.get(['dateCreation'])!.value,
      phase: this.editForm.get(['phase'])!.value,
      nbMots: this.editForm.get(['nbMots'])!.value,
      tempsSablier: this.editForm.get(['tempsSablier'])!.value,
      master: this.editForm.get(['master'])!.value,
      joueurs: this.editForm.get(['joueurs'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPartie>>): void {
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

  trackById(index: number, item: IUser): any {
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
