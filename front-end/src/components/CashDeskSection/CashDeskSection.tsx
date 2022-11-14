import * as React from 'react';
import './Desk.css';
import './Desks.css';
import './CashDeskSection.css';
import Client from '../Client/Client';
import Queue from '../Queue/Queue';
import {useState, useEffect} from 'react';
const CashDeskSection = (props:any) => {
  const [queues,setQueues]=useState([...props.queues]);
  function createDesks(desksNumber:number):any{
    let desks=[];
    for(let i:number=0;i<desksNumber;i++){
      desks.push(
        <div className='desk'></div>
      )
    }
    return desks;
  }
  useEffect(()=>{
    setQueues(props.queues)
  },[props.queues]);
  return (
    <div className='cash-desk-section'>
      <div className='desks'>
      {
        ((desks:any)=>{
          return createDesks(desks.length)
        })(queues)
      }
      </div>
      <div className="queues">
        {
          queues.map((queue:any,i:number)=>{
            return <Queue clients={queue.clients} number={i}/>
          })
        }
      </div>
    </div>
  );
};

export default CashDeskSection;