export enum PizzaState {
  Waiting,
  Dough,
  Filling,
  Baking,
  Ready
}

export interface IPizza {
  id: number;
  name: string;
  pizzaState: PizzaState;
}

export interface ICook {
  id: number;
  cookType: string;
  isWorking: boolean;
  isStop: boolean;
  pizzaCreationMinTimeInSec: number;
  pizza: IPizza;
}

export type Cooks = Array<ICook>;