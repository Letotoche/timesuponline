import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimesuponlineSharedModule } from 'app/shared/shared.module';
import { ReclamationComponent } from './reclamation.component';
import { ReclamationDetailComponent } from './reclamation-detail.component';
import { ReclamationUpdateComponent } from './reclamation-update.component';
import { ReclamationDeleteDialogComponent } from './reclamation-delete-dialog.component';
import { reclamationRoute } from './reclamation.route';

@NgModule({
  imports: [TimesuponlineSharedModule, RouterModule.forChild(reclamationRoute)],
  declarations: [ReclamationComponent, ReclamationDetailComponent, ReclamationUpdateComponent, ReclamationDeleteDialogComponent],
  entryComponents: [ReclamationDeleteDialogComponent]
})
export class TimesuponlineReclamationModule {}
