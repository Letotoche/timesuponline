import { IUser } from 'app/core/user/user.model';
import { IPartie } from 'app/shared/model/partie.model';
import { IReclamation } from 'app/shared/model/reclamation.model';
import { ITourDeJeu } from 'app/shared/model/tour-de-jeu.model';
import { EtatMot } from 'app/shared/model/enumerations/etat-mot.model';

export interface IMot {
  id?: number;
  mot?: string;
  etat?: EtatMot;
  auteur?: IUser;
  partie?: IPartie;
  reclamation?: IReclamation;
  tourDeJeu?: ITourDeJeu;
}

export class Mot implements IMot {
  constructor(
    public id?: number,
    public mot?: string,
    public etat?: EtatMot,
    public auteur?: IUser,
    public partie?: IPartie,
    public reclamation?: IReclamation,
    public tourDeJeu?: ITourDeJeu
  ) {}
}
