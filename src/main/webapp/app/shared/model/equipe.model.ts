import { IUser } from 'app/core/user/user.model';
import { IPartie } from 'app/shared/model/partie.model';

export interface IEquipe {
  id?: number;
  nom?: string;
  score1?: number;
  score2?: number;
  score3?: number;
  membres?: IUser[];
  partie?: IPartie;
}

export class Equipe implements IEquipe {
  constructor(
    public id?: number,
    public nom?: string,
    public score1?: number,
    public score2?: number,
    public score3?: number,
    public membres?: IUser[],
    public partie?: IPartie
  ) {}
}
