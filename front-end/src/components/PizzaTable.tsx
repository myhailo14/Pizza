import { DetailsList, Dialog, DialogType, IColumn, SelectionMode } from "@fluentui/react";
import { Dispatch, FunctionComponent, SetStateAction } from "react";
import { Clients, IPizzaViewModel, PizzaState } from "../models";

export interface IPizzaTableProps {
  clients: Clients;
  show: boolean;
  setShow: Dispatch<SetStateAction<boolean>>;
}

const PizzaTable: FunctionComponent<IPizzaTableProps> = (props: IPizzaTableProps) => {
  const pizzas: IPizzaViewModel[] = props.clients.map(client => {
    return client.order.map(pizza => {
      return {
        clientId: client.id,
        queueNumber: client.queueNumber,
        pizzaId: pizza.id,
        name: pizza.name,
        state: PizzaState[pizza.pizzaState]
      }
    });
  }).flatMap(x => x);

  const dialogProps = {
    type: DialogType.normal,
    title: 'Pizza Table'
  }

  const columns: IColumn[] = [
    {
      key: 'clientId',
      name: 'Client Id',
      fieldName: 'clientId',
      minWidth: 12
    },
    {
      key: 'queueNumber',
      name: 'Queue Number',
      fieldName: 'queueNumber',
      minWidth: 12
    },
    {
      key: 'pizzaId',
      name: 'Pizza Id',
      fieldName: 'pizzaId',
      minWidth: 12
    },
    {
      key: 'name',
      name: 'Name',
      fieldName: 'name',
      minWidth: 30
    },
    {
      key: 'state',
      name: 'State',
      fieldName: 'state',
      minWidth: 30
    }
  ];
  
  return (
    <Dialog 
      hidden={!props.show}
      onDismiss={() => props.setShow(false)}
      dialogContentProps={dialogProps}
    >
      <DetailsList 
        isHeaderVisible={true} 
        selectionMode={SelectionMode.none} 
        columns={columns} 
        items={pizzas} 
      />
    </Dialog>
  );
};

export default PizzaTable;