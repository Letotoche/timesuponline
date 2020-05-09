import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TimesuponlineSharedModule } from 'app/shared/shared.module';
import { TourDeJeuComponent } from './tour-de-jeu.component';
import { TourDeJeuDetailComponent } from './tour-de-jeu-detail.component';
import { TourDeJeuUpdateComponent } from './tour-de-jeu-update.component';
import { TourDeJeuDeleteDialogComponent } from './tour-de-jeu-delete-dialog.component';
import { tourDeJeuRoute } from './tour-de-jeu.route';

@NgModule({
  imports: [TimesuponlineSharedModule, RouterModule.forChild(tourDeJeuRoute)],
  declarations: [TourDeJeuComponent, TourDeJeuDetailComponent, TourDeJeuUpdateComponent, TourDeJeuDeleteDialogComponent],
  entryComponents: [TourDeJeuDeleteDialogComponent]
})
export class TimesuponlineTourDeJeuModule {}
