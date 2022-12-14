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
  pizzaState: string;
}

export interface ICook {
  id: number;
  cookType: string;
  working: boolean;
  stop: boolean;
  pizzaCreationMinTimeInSec: number;
  pizza: IPizza;
}

export interface IClient {
  order: Pizzas;
  queueNumber: number;
  id: number;
}

export interface IPizzaViewModel {
  clientId: number,
  queueNumber: number
  pizzaId: number,
  name: string,
  state: string
}

export interface IQueue {
  clients: Clients;
}

export type Cooks = Array<ICook>;
export type Pizzas = Array<IPizza>;
export type Clients = Array<IClient>;