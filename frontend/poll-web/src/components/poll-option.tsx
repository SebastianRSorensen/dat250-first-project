import { useMutation } from "@tanstack/react-query";
import type { PollOption } from "../interfaces";
import { Button } from "./manual-install/button";
import { CheckCircle2 } from "lucide-react";
import { castVote } from "../services";
import { cn } from "../utils/cn";

export function PollOption({
  option,
  isVoted,
  pollId,
  userId,
}: {
  option: PollOption;
  isVoted: boolean;
  pollId: string;
  userId: string;
}) {
  const { voteOptionId, caption, votes } = option ?? {};

  const vote = useMutation({
    mutationFn: async (data: { isUpVote: boolean }) =>
      castVote(userId, pollId, voteOptionId, data.isUpVote),
    onSuccess: (res) => {
      console.log(res);
    },
    onError: (err) => {
      console.log(err);
    },
  });

  return (
    <div className="w-full h-10 flex flex-row items-center justify-between">
      <span>{caption}</span>
      <span className="flex flex-row items-center space-x-5">
        <span>
          {isVoted && <CheckCircle2 className="mr-2 h-4 w-4" />}
          {`${votes} votes`}
        </span>
        <Button
          key={option.voteOptionId}
          onClick={() =>
            vote.mutate({
              isUpVote: true,
            })
          }
          className={cn(
            "flex justify-end",

            isVoted ? "bg-primary text-primary-foreground" : "bg-secondary"
          )}
          disabled={isVoted}
        ></Button>
      </span>
    </div>
  );
}
