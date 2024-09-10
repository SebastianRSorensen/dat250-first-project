import { CreatePoll } from "./components/create-poll";
import { CreateUser } from "./components/create-user";
import { PageWrapper } from "./components/page-wrapper";
import { Poll } from "./components/poll";

export function App() {
  return (
    <PageWrapper>
      <CreateUser />
      <CreatePoll />
      <Poll />
    </PageWrapper>
  );
}
