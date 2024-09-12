import { useState, useEffect } from "react";
import { Button } from "./manual-install/button";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "./manual-install/card";

import { Poll as PollType } from "../interfaces";
import { PollOption } from "./poll-option";

export function Poll({ poll }: { poll: PollType }) {
  if (!poll) return <div className="text-center">No poll data available</div>;

  return (
    <Card className="w-full max-w-md mx-auto">
      <CardHeader>
        <CardTitle>{poll.question}</CardTitle>
      </CardHeader>
      <CardContent className="space-y-4">
        {Object.values(poll.options).map((option) => (
          <PollOption
            key={option.voteOptionId}
            option={option}
            isVoted={false}
            pollId={poll.pollId}
            userId={"82e76674-04ca-49e9-b451-6299fe5d5f9b"}
          />
        ))}
        <p className="text-sm text-muted-foreground text-center">
          {/* Total votes: {totalVotes} */}
        </p>
        {/* {userVote !== null && (
          <p className="text-sm text-muted-foreground text-center">
            You have voted for:{" "}
            {poll.options.find((o) => o.id === userVote)?.text}
          </p>
        )} */}
      </CardContent>
    </Card>
  );
}
