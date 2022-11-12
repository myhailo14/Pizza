import React, { useEffect, useState } from 'react';
import Client from '../Client/Client';
import './Queue.css';
import _ from 'lodash';
const Queue = (props:any) => {
  const [clients,setClients]=useState([...props.clients]);
  let servicedClients:any[]=[];
  function makeClientsGoAway(clients:any):void{
    for(let i:number=0;i<clients.length;i++){
      let client=document.querySelector(`[data-id="${clients[i].id}"]`);
      client?.classList.add("client-move-down");
    }
  }
  function isEqual(client1:any,client2:any):boolean{
    if(client1.id===client2.id){
      return true;
    }else{
      return false;
    }
  }
  useEffect(()=>{
    servicedClients= _.differenceWith(clients, props.clients,isEqual);
    console.log(servicedClients);
    makeClientsGoAway(servicedClients);
    setClients([...servicedClients,...props.clients]);
  },[props.clients]);
  return (
    <div className='queue' data-queueNumber={props.number}>
      {
        clients.map((client:any)=>{
          let foundClient=_.find(servicedClients,(sClient:any)=>{return isEqual(sClient,client)});
          let classes="";
          if(foundClient===null){
            classes+="client-move-down";
          }
          return <Client info={client} classes={classes}/>
        })
      }
    </div>
  );
};

export default Queue;