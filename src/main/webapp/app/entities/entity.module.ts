import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'partie',
        loadChildren: () => import('./partie/partie.module').then(m => m.TimesuponlinePartieModule)
      },
      {
        path: 'equipe',
        loadChildren: () => import('./equipe/equipe.module').then(m => m.TimesuponlineEquipeModule)
      },
      {
        path: 'mot',
        loadChildren: () => import('./mot/mot.module').then(m => m.TimesuponlineMotModule)
      },
      {
        path: 'reclamation',
        loadChildren: () => import('./reclamation/reclamation.module').then(m => m.TimesuponlineReclamationModule)
      },
      {
        path: 'tour-de-jeu',
        loadChildren: () => import('./tour-de-jeu/tour-de-jeu.module').then(m => m.TimesuponlineTourDeJeuModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class TimesuponlineEntityModule {}
