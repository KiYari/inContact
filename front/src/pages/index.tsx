import {FC} from "react";
import Layout from "@/layout/Layout";
import Chat from "@/components/chat/Chat";

interface HomeProps {

}

const Home: FC<HomeProps> = () => {

    return(<Layout>
            <Chat/>
        </Layout>
  )
}

export default Home;