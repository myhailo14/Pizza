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
  working: boolean;
  stop: boolean;
  pizzaCreationMinTimeInSec: number;
  pizza: IPizza;
}

export type Cooks = Array<ICook>;