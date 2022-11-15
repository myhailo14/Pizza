import CashDeskSection from "./components/CashDeskSection/CashDeskSection";
import { DefaultButton, DocumentCard, PrimaryButton, registerIcons } from "@fluentui/react";
import { PlayResumeIcon, CirclePauseIcon, ChevronDownIcon } from '@fluentui/react-icons-mdl2';
import Kitchen from "./components/Kitchen";
import './App.scss';
import { useCallback, useEffect, useState } from "react";
import { Cooks, IQueue, PizzaState } from "./models";
import Config from "./components/Config";
import PizzaTable from "./components/PizzaTable";

registerIcons({
  icons: {
    PlayResume: <PlayResumeIcon />,
    CirclePause: <CirclePauseIcon />,
    ChevronDown: <ChevronDownIcon />
  }
});

let cookInterval: NodeJS.Timeout;
let queueInterval: NodeJS.Timeout;

const App = () => {
  const [isPizzeriaWorking, setIsPizzeriaWorking] = useState<boolean>(false);
  const [cooks, setCooks] = useState<Cooks | null>(null);
  const [isConfigHidden, setConfigHidden] = useState<boolean>(true);
  const [queues, setQueues] = useState<IQueue[]>([]);
  const [showTable, setShowTable] = useState<boolean>(false);


  useEffect(() => {
    if (!isPizzeriaWorking) {
      clearInterval(cookInterval);
      clearInterval(queueInterval);
      return;
    }

    cookInterval = setInterval(async () => {
      const response = await fetch('http://localhost:8080/cooks');
      const cooks = (await response.json()) as Cooks;
      setCooks(cooks);
    }, 100);

    queueInterval = setInterval(async () => {
      const response = await fetch("http://localhost:8080/queues");
      if (response.ok) {
        const queuesArr = (await response.json()) as IQueue[];
        setQueues(queuesArr);
      } else {
        alert("Error HTTP: " + response.status);
      }
    }, 100)

  }, [isPizzeriaWorking]);

  const stopCook = useCallback((id: number) => {
    fetch(`http://localhost:8080/stop-cook/${id}`, {
      method: 'PUT'
    });
  }, []);

  const resumeCook = useCallback((id: number) => {
    fetch(`http://localhost:8080/resume-cook/${id}`, {
      method: 'PUT'
    });
  }, []);

  const startApp = () => {
    fetch(`http://localhost:8080/start`, {
      method: 'POST'
    });
    setIsPizzeriaWorking(true);

  }

  const stopApp = () => {
    fetch(`http://localhost:8080/stop`, {
      method: 'POST'
    });
    setIsPizzeriaWorking(false);
    setCooks([]);
    setQueues([]);
  }

  return (
    <>
      <DocumentCard className='app-wrapper'>
        <CashDeskSection queues={queues} />
        <Kitchen cooks={cooks ?? []} stopCook={stopCook} resumeCook={resumeCook} />
        <DocumentCard className='buttons-container'>
          <DefaultButton text='Configuration' onClick={() => setConfigHidden(false)} />
          <PrimaryButton text='Start' onClick={() => startApp()} />
          <PrimaryButton text='View Table' onClick={() => setShowTable(true)} />
          {/* <PrimaryButton text='Stop' onClick={() => stopApp()} /> */}
        </DocumentCard>
        <Config isHidden={isConfigHidden} hiddenChanger={setConfigHidden} />
        <PizzaTable clients={queues.flatMap(x => x.clients)} show={showTable} setShow={setShowTable} />
      </DocumentCard>
    </>
  );
}

export default App;
