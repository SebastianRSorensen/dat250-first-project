import { useQuery } from "@tanstack/react-query";
import { CreatePoll } from "./components/create-poll";
import { CreateUser } from "./components/create-user";
import { PageWrapper } from "./components/page-wrapper";
import { Poll } from "./components/poll";
import { getPolls } from "./services";

export function App() {
  const {
    data: polls,
    isLoading,
    error,
  } = useQuery({
    queryKey: ["polls"],
    queryFn: getPolls,
  });

  if (isLoading) return <div>Loading...</div>;
  if (error) return <div>An error occurred: {error.message}</div>;

  return (
    <PageWrapper>
      <CreateUser />
      <CreatePoll />
      {polls?.map((poll) => (
        // <div>{poll.question}</div>
        <Poll poll={poll} />
      ))}
    </PageWrapper>
  );
}
