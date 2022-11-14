import { DefaultButton, DocumentCard, PrimaryButton, registerIcons } from "@fluentui/react";
import { PlayResumeIcon, CirclePauseIcon } from '@fluentui/react-icons-mdl2';
import Kitchen from "./components/Kitchen";
import './App.scss';
import { useCallback, useEffect, useState } from "react";
import { Cooks } from "./models";
import Config from "./components/Config";

registerIcons({
  icons: {
    PlayResume: <PlayResumeIcon />,
    CirclePause: <CirclePauseIcon />
  }
});

let interval: NodeJS.Timeout;

const App = () => {
  const [isPizzeriaWorking, setIsPizzeriaWorking] = useState<boolean>(false);
  const [cooks, setCooks] = useState<Cooks | null>(null);
  const [isConfigHidden, setConfigHidden] = useState<boolean>(true);

  useEffect(() => {
    if (!isPizzeriaWorking) {
      clearInterval(interval);
      return;
    }

    interval = setInterval(async () => {
      const response = await fetch('http://localhost:8080/cooks');
      const cooks = (await response.json()) as Cooks;
      setCooks(cooks);
    }, 100);
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

  return (
    <DocumentCard className='app-wrapper'>
      <div></div>
      <Kitchen cooks={cooks ?? []} stopCook={stopCook} resumeCook={resumeCook} />
      <DocumentCard className='buttons-container'>
        <DefaultButton text='Configuration' onClick={() => setConfigHidden(false)} />
        <PrimaryButton text='Start' />
        <PrimaryButton text='Stop' />
      </DocumentCard>
      <Config isHidden={isConfigHidden} hiddenChanger={setConfigHidden}/>
    </DocumentCard>
  );
}

export default App;
