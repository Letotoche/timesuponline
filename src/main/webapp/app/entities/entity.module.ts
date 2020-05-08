import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'partie',
        loadChildren: () => import('./partie/partie.module').then(m => m.TimesuponlinePartieModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TimesuponlineEntityModule {}
