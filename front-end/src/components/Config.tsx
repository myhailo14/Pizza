import { DefaultButton, Dialog, DialogFooter, DialogType, Dropdown, IDropdownOption, PrimaryButton, Stack, TextField } from "@fluentui/react";
import React, { Dispatch, SetStateAction, useState } from "react";

export interface IConfigProps {
    isHidden: boolean,
    hiddenChanger: Dispatch<SetStateAction<boolean>>,
}

interface IConfigDto {
    payDesksNumber: number,
    cooksNumber: number,
    pizzasNumber: number,
    pizzaCreationMinTimeInSec: number,
    cookWorkingStrategy: string,
    clientsGenerationStrategy: string
}

const Config: React.FunctionComponent<IConfigProps> = (props: IConfigProps) => {

    const [payDesksNumber, setPayDesksNumber] = useState<number>(1);
    const [cooksNumber, setCooksNumber] = useState<number>(1);
    const [pizzasNumber, setPizzasNumber] = useState<number>(1);
    const [pizzaCreationMinTimeInSec, setCreationTime] = useState<number>(1);
    const [cookWorkingStrategy, setCookStrategy] = useState<string>('alone');
    const [clientsGenerationStrategy, setGenerationStrategy] = useState<string>('default');

    const dialogContentProps = {
        type: DialogType.normal,
        title: 'Pizzeria config',
        closeButtonAriaLabel: 'Close',
        subText: 'Here you can config your pizzeria.',
    };

    const handleFooterClick = async (sendRequest: boolean) => {
        if (sendRequest) {
            const response = await fetch(`http://localhost:8080/config`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                    // 'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: JSON.stringify({
                    payDesksNumber: payDesksNumber,
                    cooksNumber: cooksNumber,
                    pizzasNumber: pizzasNumber,
                    pizzaCreationMinTimeInSec: pizzaCreationMinTimeInSec,
                    cookWorkingStrategy: cookWorkingStrategy,
                    clientsGenerationStrategy: clientsGenerationStrategy
                })

            })
            if(response.ok){
                window.alert("Your config was applied");
            } else {
                window.alert("ERROR APPLYIN CONFIG! ERROR HTTP: " + response.status);
            }
        }
        props.hiddenChanger(true);

    }

    const pizzaStrategiesOptions: IDropdownOption[] =
        [
            { key: 'alone', text: "One cook - full pizza" },
            { key: 'part', text: "One cook - one process" }
        ]

    const clientStrategiesOptions = [
        { key: 'default', text: "Fixed Genertion" },
        { key: 'random', text: "Random Generation" }
    ]

    const handleInputChange = (event: React.FormEvent<HTMLInputElement | HTMLTextAreaElement>, value: string | undefined) => {
        const inputValue = Number.parseInt(value!);
       
        
        switch (event.currentTarget.name) {
            case "desks":
                setPayDesksNumber(inputValue);
                break;
            case "cooks":
                setCooksNumber(inputValue);
                break;
            case "pizzas":
                setPizzasNumber(inputValue);
                break;
            case "time":
                setCreationTime(inputValue);
                break;
        }
    }

    const handleCookStrategyChange = (event: React.FormEvent<HTMLDivElement>, option: IDropdownOption<any> | undefined) => {

        setCookStrategy(option!.key as string);
    }

    const handleClientStrategyChange = (event: React.FormEvent<HTMLDivElement>, option: IDropdownOption<any> | undefined) => {

        setGenerationStrategy(option!.key as string);
    }

    return (
        <Dialog
            hidden={props.isHidden}
            dialogContentProps={dialogContentProps}>
            <Stack tokens={{ childrenGap: 10 }}>
                <TextField name="desks" suffix="pcs" label="Pay desks number" type="number" min={0} step={1} onChange={handleInputChange}></TextField>
                <TextField name="cooks" suffix="pcs" label="Cooks Number" type="number" min={0} step={1} onChange={handleInputChange}></TextField>
                <TextField name="pizzas" suffix="pcs" label="Minimal pizzas number" type="number" min={0} step={1} onChange={handleInputChange}></TextField>
                <TextField name="time" suffix="sec" label="Minimal pizza cooking time" type="number" min={0} step={1} onChange={handleInputChange}></TextField>
                <Dropdown label="Pizza cooking strategy" onChange={handleCookStrategyChange} placeholder="Select strategy" options={pizzaStrategiesOptions}></Dropdown>
                <Dropdown label="Client generation strategy" onChange={handleClientStrategyChange} placeholder="Select strategy" options={clientStrategiesOptions}></Dropdown>
            </Stack>
            <DialogFooter>
                <PrimaryButton onClick={() => handleFooterClick(true)}>Save</PrimaryButton>
                <DefaultButton onClick={() => handleFooterClick(false)}>Cancel</DefaultButton>
            </DialogFooter>
        </Dialog>
    );
}

export default Config;