import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimesuponlineSharedModule } from 'app/shared/shared.module';
import { MotComponent } from './mot.component';
import { MotDetailComponent } from './mot-detail.component';
import { MotUpdateComponent } from './mot-update.component';
import { MotDeleteDialogComponent } from './mot-delete-dialog.component';
import { motRoute } from './mot.route';

@NgModule({
  imports: [TimesuponlineSharedModule, RouterModule.forChild(motRoute)],
  declarations: [MotComponent, MotDetailComponent, MotUpdateComponent, MotDeleteDialogComponent],
  entryComponents: [MotDeleteDialogComponent]
})
export class TimesuponlineMotModule {}
