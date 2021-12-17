import { render, screen } from '@testing-library/react';
import TileNftApp from '../../TileNftApp';

test('renders learn react link', () => {
  render(<TileNftApp />);
  const linkElement = screen.getByText(/learn react/i);
  expect(linkElement).toBeInTheDocument();
});
