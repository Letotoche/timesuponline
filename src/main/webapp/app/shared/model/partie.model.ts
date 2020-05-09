import { Moment } from 'moment';
import { IEquipe } from 'app/shared/model/equipe.model';
import { IUser } from 'app/core/user/user.model';
import { IMot } from 'app/shared/model/mot.model';
import { PhasePartie } from 'app/shared/model/enumerations/phase-partie.model';

export interface IPartie {
  id?: number;
  intitule?: string;
  dateCreation?: Moment;
  phase?: PhasePartie;
  nbMots?: number;
  tempsSablier?: number;
  equipes?: IEquipe[];
  master?: IUser;
  joueurs?: IUser[];
  paquets?: IMot[];
}

export class Partie implements IPartie {
  constructor(
    public id?: number,
    public intitule?: string,
    public dateCreation?: Moment,
    public phase?: PhasePartie,
    public nbMots?: number,
    public tempsSablier?: number,
    public equipes?: IEquipe[],
    public master?: IUser,
    public joueurs?: IUser[],
    public paquets?: IMot[]
  ) {}
}
