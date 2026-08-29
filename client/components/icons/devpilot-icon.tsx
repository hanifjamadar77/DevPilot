import type { SVGProps } from "react";

import { cn } from "@/lib/utils";

type DevPilotIconProps = SVGProps<SVGSVGElement> & {
  variant?: "color" | "mono";
};

export function DevPilotIcon({
  className,
  variant = "color",
  ...props
}: DevPilotIconProps) {
  const mono = variant === "mono";

  return (
    <svg
      viewBox="0 0 64 64"
      fill="none"
      xmlns="http://www.w3.org/2000/svg"
      aria-hidden="true"
      className={cn("shrink-0", className)}
      {...props}
    >
      {/* Main background */}
      <rect
        x="4"
        y="4"
        width="56"
        height="56"
        rx="16"
        fill={mono ? "currentColor" : "#18181B"}
      />

      {/* Code symbol */}
      <path
        d="M27 22L18 32L27 42"
        stroke={mono ? "white" : "#FFFFFF"}
        strokeWidth="4"
        strokeLinecap="round"
        strokeLinejoin="round"
      />

      <path
        d="M37 22L46 32L37 42"
        stroke={mono ? "white" : "#FFFFFF"}
        strokeWidth="4"
        strokeLinecap="round"
        strokeLinejoin="round"
      />

      {/* Center lightning / pilot symbol */}
      <path
        d="M34 18L26 33H32L30 46L40 29H34L34 18Z"
        fill={mono ? "white" : "#60A5FA"}
      />
    </svg>
  );
}