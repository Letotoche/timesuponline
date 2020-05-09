import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { IMot } from 'app/shared/model/mot.model';
import { IPartie } from 'app/shared/model/partie.model';

export interface ITourDeJeu {
  id?: number;
  tempsRestant?: Moment;
  dateDebute?: Moment;
  user?: IUser;
  mots?: IMot[];
  partie?: IPartie;
}

export class TourDeJeu implements ITourDeJeu {
  constructor(
    public id?: number,
    public tempsRestant?: Moment,
    public dateDebute?: Moment,
    public user?: IUser,
    public mots?: IMot[],
    public partie?: IPartie
  ) {}
}
