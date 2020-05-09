import { IUser } from 'app/core/user/user.model';
import { IMot } from 'app/shared/model/mot.model';
import { EtatReclamation } from 'app/shared/model/enumerations/etat-reclamation.model';

export interface IReclamation {
  id?: number;
  etat?: EtatReclamation;
  auteur?: IUser;
  mot?: IMot;
}

export class Reclamation implements IReclamation {
  constructor(public id?: number, public etat?: EtatReclamation, public auteur?: IUser, public mot?: IMot) {}
}
