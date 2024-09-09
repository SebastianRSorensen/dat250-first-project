export type User = {
  username: string;
  email: string;
  polls: Poll[];
};

export type Users = User[];

type PollOption = {
  presentationOrder: number;
  caption: string;
};

type VoteDetail = {
  voter: string;
  selectedOption: PollOption;
  voteTime: string;
};

type PollVotes = {
  [voterName: string]: VoteDetail;
};

type Poll = {
  pollId: number;
  question: string;
  options: PollOption[];
  creator: string;
  votes: PollVotes;
  createdAt: string;
  closesAt: string;
};
