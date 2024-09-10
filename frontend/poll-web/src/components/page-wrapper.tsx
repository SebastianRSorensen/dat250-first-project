import { ReactNode } from "react";

export function PageWrapper({ children }: { children: ReactNode }) {
  return <div className="space-y-5 p-10">{children}</div>;
}
