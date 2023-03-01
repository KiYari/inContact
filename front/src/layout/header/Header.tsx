import React, {FC, useEffect, useState} from "react";
import { Menu, MenuProps} from "antd";
import styles from './header.module.css';
import Link from "next/link";

interface headerProps {

}

class MenuItem {
}

function getItem(
    label: React.ReactNode,
    key: React.Key,
    icon?: React.ReactNode,
    children?: MenuItem[],
    type?: 'group',
): MenuItem {
    return {
        key,
        icon,
        children,
        label,
        type,
    } as MenuItem;
}

const App: FC<headerProps> = () => {
    const [isLoading, setIsLoading] = useState(false)
    const links = ['/', '/auth/Login']
    const items1: MenuProps['items'] = ['Home', 'Login'].map((title, key) => ({
        key: key,
        label: (<Link onClick={()=> {
            localStorage.setItem('selectedKey', String(key))
        }} href={links[key]}>{`${title}`}</Link>),
        className: styles.headerItem,
    }));

    useEffect(() => {
        setIsLoading(true)
    })

    return(
        <header className={styles.header}>
            {isLoading &&  <Menu
                className={styles.menu}
                defaultSelectedKeys={[localStorage.getItem('selectedKey') || '0']}
                mode="horizontal"
                items={items1}/>
            }

        </header>
    )
}

export default App;