import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimesuponlineSharedModule } from 'app/shared/shared.module';
import { EquipeComponent } from './equipe.component';
import { EquipeDetailComponent } from './equipe-detail.component';
import { EquipeUpdateComponent } from './equipe-update.component';
import { EquipeDeleteDialogComponent } from './equipe-delete-dialog.component';
import { equipeRoute } from './equipe.route';

@NgModule({
  imports: [TimesuponlineSharedModule, RouterModule.forChild(equipeRoute)],
  declarations: [EquipeComponent, EquipeDetailComponent, EquipeUpdateComponent, EquipeDeleteDialogComponent],
  entryComponents: [EquipeDeleteDialogComponent]
})
export class TimesuponlineEquipeModule {}
