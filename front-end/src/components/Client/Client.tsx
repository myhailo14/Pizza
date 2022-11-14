import { useEffect, useRef } from "react";
import './Client.css';
const Client = (props:any) => {
  const infoLabel=useRef<any>(null);
  const clientBody=useRef<any>(null);
  function showInfo():void{
    if(infoLabel!==null){
      infoLabel.current.style.display="block";
    }
  }
  function hideInfo():void{
    if(infoLabel!==null){
      infoLabel.current.style.display="none";
    }
  }
  function changeBodyColor(){
    if(clientBody!==null){
      let color:number=Math.floor(Math.random() * 360);
      clientBody.current.style.backgroundColor=`hsl(${color},100%,50%)`;
    }
  }
  useEffect(()=>{
    changeBodyColor();
    hideInfo();
  },[])
  return (
    <div className={`client ${props.classes}`} data-id={props.info.id}>
      <div className="client-body" onMouseEnter={showInfo} onMouseLeave={hideInfo} ref={clientBody}>
        <div className="client-head"></div>
      </div>
      <div className="client-info" ref={infoLabel} onMouseEnter={showInfo} onMouseLeave={hideInfo}>
        <ul className="pizzas">
          {
            props.info.order.map((pizza:any, index: number)=>{
              return <li key={pizza.id}>{pizza.name}</li>
            })
          }
        </ul>
      </div>
    </div>
  );
};

export default Client;