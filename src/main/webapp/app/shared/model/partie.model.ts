import { IUser } from 'app/core/user/user.model';

export interface IPartie {
  id?: number;
  intitule?: string;
  joueurs?: IUser[];
}

export class Partie implements IPartie {
  constructor(public id?: number, public intitule?: string, public joueurs?: IUser[]) {}
}
