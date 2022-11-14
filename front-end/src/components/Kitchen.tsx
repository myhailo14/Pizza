import { DocumentCard } from "@fluentui/react";
import { FunctionComponent } from "react";
import { Cooks } from "../models";
import Cook from "./Cook";
import './Kitchen.scss';

export interface IKitchenProps {
  cooks: Cooks;
  resumeCook: (id: number) => void;
  stopCook: (id: number) => void; 
}

const Kitchen: FunctionComponent<IKitchenProps> = (props: IKitchenProps) => {
  return (
    <DocumentCard className='kitchen-wrapper'>
      {props.cooks.map(cook => (
        <Cook 
          key={cook.id} 
          data={cook} 
          resume={props.resumeCook.bind(null, cook.id)} 
          stop={props.stopCook.bind(null, cook.id)}
        />
      ))}
    </DocumentCard>
  );
};

export default Kitchen;