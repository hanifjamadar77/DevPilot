import type { ComponentPropsWithoutRef } from "react";
import { cn } from "@/lib/utils";
import { RiLoaderLine } from "@remixicon/react";

type SpinnerProps = Omit<ComponentPropsWithoutRef<"svg">, "children">;

function Spinner({ className, ...props }: SpinnerProps) {
  return (
    <RiLoaderLine
      data-slot="spinner"
      role="status"
      aria-label="Loading"
      className={cn("size-4 animate-spin", className)}
      {...props}
    />
  );
}

export { Spinner };
