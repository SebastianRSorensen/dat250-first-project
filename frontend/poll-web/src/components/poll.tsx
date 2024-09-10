import { useState, useEffect } from "react";
import { Button } from "./manual-install/button";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "./manual-install/card";
import { CheckCircle2 } from "lucide-react";

interface PollOption {
  id: number;
  text: string;
  votes: number;
}

interface Poll {
  id: number;
  question: string;
  options: PollOption[];
}

// In a real app, you'd fetch this data from an API
const mockPoll: Poll = {
  id: 1,
  question: "What's your favorite programming language?",
  options: [
    { id: 1, text: "JavaScript", votes: 0 },
    { id: 2, text: "Python", votes: 0 },
    { id: 3, text: "Java", votes: 0 },
    { id: 4, text: "C#", votes: 0 },
  ],
};

export function Poll() {
  const [poll, setPoll] = useState<Poll | null>(null);
  const [userVote, setUserVote] = useState<number | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    // Simulate API call to fetch poll data
    const fetchPoll = async () => {
      try {
        // In a real app, you'd fetch data from an API here
        await new Promise((resolve) => setTimeout(resolve, 1000)); // Simulate network delay
        setPoll(mockPoll);
        // Simulate fetching user's previous vote (if any)
        const previousVote = localStorage.getItem(`poll_${mockPoll.id}_vote`);
        if (previousVote) {
          setUserVote(parseInt(previousVote));
        }
      } catch (err) {
        setError("Failed to load poll data");
      } finally {
        setIsLoading(false);
      }
    };

    fetchPoll();
  }, []);

  const handleVote = (optionId: number) => {
    if (userVote !== null) {
      setError("You have already voted");
      return;
    }

    setPoll((currentPoll) => {
      if (!currentPoll) return null;
      return {
        ...currentPoll,
        options: currentPoll.options.map((option) =>
          option.id === optionId
            ? { ...option, votes: option.votes + 1 }
            : option
        ),
      };
    });

    setUserVote(optionId);
    // In a real app, you'd send this vote to your backend
    localStorage.setItem(`poll_${mockPoll.id}_vote`, optionId.toString());
  };

  if (isLoading) return <div className="text-center">Loading poll...</div>;
  if (error) return <div className="text-center text-red-500">{error}</div>;
  if (!poll) return <div className="text-center">No poll data available</div>;

  const totalVotes = poll.options.reduce(
    (sum, option) => sum + option.votes,
    0
  );

  return (
    <Card className="w-full max-w-md mx-auto">
      <CardHeader>
        <CardTitle>{poll.question}</CardTitle>
      </CardHeader>
      <CardContent className="space-y-4">
        {poll.options.map((option) => (
          <Button
            key={option.id}
            onClick={() => handleVote(option.id)}
            className={`w-full justify-between ${
              userVote === option.id
                ? "bg-primary text-primary-foreground"
                : "bg-secondary"
            }`}
            disabled={userVote !== null}
          >
            <span>{option.text}</span>
            <span className="flex items-center">
              {userVote === option.id && (
                <CheckCircle2 className="mr-2 h-4 w-4" />
              )}
              {((option.votes / totalVotes) * 100 || 0).toFixed(1)}% (
              {option.votes} votes)
            </span>
          </Button>
        ))}
        <p className="text-sm text-muted-foreground text-center">
          Total votes: {totalVotes}
        </p>
        {userVote !== null && (
          <p className="text-sm text-muted-foreground text-center">
            You have voted for:{" "}
            {poll.options.find((o) => o.id === userVote)?.text}
          </p>
        )}
      </CardContent>
    </Card>
  );
}
