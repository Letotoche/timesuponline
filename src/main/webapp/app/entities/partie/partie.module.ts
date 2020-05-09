import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimesuponlineSharedModule } from 'app/shared/shared.module';
import { PartieComponent } from './partie.component';
import { PartieDetailComponent } from './partie-detail.component';
import { PartieUpdateComponent } from './partie-update.component';
import { PartieDeleteDialogComponent } from './partie-delete-dialog.component';
import { partieRoute } from './partie.route';

@NgModule({
  imports: [TimesuponlineSharedModule, RouterModule.forChild(partieRoute)],
  declarations: [PartieComponent, PartieDetailComponent, PartieUpdateComponent, PartieDeleteDialogComponent],
  entryComponents: [PartieDeleteDialogComponent]
})
export class TimesuponlinePartieModule {}
